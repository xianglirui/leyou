## 开发环境

### 环境准备

1. JDK 10
2. tomcat 8+
3. mysql & mysql driver 8+
4. maven 
5. node
6. vue
7. vue cli
8. webpack 3.6.0&emsp; `webpack配置相当麻烦，本项目为vue cli2，为了顺利跑起来，请用3.6.0版本的webpack`

*环境配置完后，vue项目打开终端输入:*

```
# 启动项目
npm run dev

# 项目打包
npm run build

```



<br/>

<br/>

<br/>

<br/>

## 服务器环境

### 1.环境搭建

本项目通过docker部署至服务器，docker是一个容器，可以让开发者将他的应用及其依赖包打包发布到其他linux机器上，减少为调配环境浪费的时间精力，一个字，省事

云服务器是通过学生认证免费领的阿里云，系统是ubuntn

> Ubuntu安装Docker步骤：

```md
# 卸载旧版本
apt-get remove docker docker-engine docker.io containerd runc
# 更新数据源
apt-get update
# 安装所需依赖
apt-get -y install apt-transport-https ca-certificates curl software-properties-common
# 安装 GPG 证书
curl -fsSL http://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo apt-key add -
# 新增数据源
add-apt-repository "deb [arch=amd64] http://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable"
# 更新并安装 Docker CE
apt-get update && apt-get install -y docker-ce

=====================================  以下不能直接复制  ==================================

#配置Docker镜像加速器：阿里云加速器，点此链接进去复制加速器地址
https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors

sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
"registry-mirrors": ["你的加速器地址"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```



> docker必会命令：

##### 搜索镜像

docker search <镜像名字>
##### 拉取镜像

docker pull <镜像名字>:<版本>
##### 删除镜像

docker rmi <镜像名字>  或   docker rmi <镜像ID> 
##### 列出本地所有镜像

docker images

##### dockerfile构建镜像

docker build -f <dockerfile文件路径> -t <镜像名称>:<版本>

##### 创建容器

docker run -p 8000:80 -tdi --privileged [imageID] [command]

```
后台启动docker,并指定宿主机端口和docker映射端口。

 -i:以交互模式运行容器，通常与 -t 同时使用；

 -d:后台运行容器，并返回容器ID；

-t:为容器重新分配一个伪输入终端，通常与 -i 同时使用；

--privileged 容器将拥有访问主机所有设备的权限

通常情况下 [command] 填下 /bin/bash 即可。
```

##### 列出所有容器

docker ps -a

##### 进入退出容器

docker exec<容器id>&emsp;||&emsp;docker exit<容器id>

##### 开启停止删除容器

docker start&nbsp;|&nbsp;stop&nbsp;|&nbsp;rm&nbsp;<容器id>

<br/>

<br/>

<br/>

<br/>

### 2.Springboot部署至docker流程：

- 将本地的jdk版本修改成jdk8 `环境变量：JAVA_HOME`

- maven添加打包插件，打成jar包（在target目录下）

  ```xml
  <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <configuration>
          <!--跳过项目运行测试用例-->
          <skipTests>true</skipTests>
      </configuration>
  </plugin>
  ```

  

- docker部署

  - linux新建目录 -> mkdir server，通过xftp将jar包传到server目录下

  - cd server，在该目录下vim dockerfile，编写dickerfile文件

  - dockerfile内容如下

    ```
    1. 定义父镜像：FROM java:8
    2. 定义作者信息：MAINTAINER byxlr
    3. 将jar包添加到容器：ADD <jar包名> <jar包新名字>
    4. 定义容器启动执行命令：CMD java -jar <jar包新名字>
    ```

  - 通过dockerfile构件镜像

    ```
    docker build -f <dockerfile文件路径> -t <镜像名称>:<版本>
    ```

  - 生成启动容器

    ```
    docker run -p 8181:8080 -id  [imageID] /bin/bash
    ```

    `注：记得在阿里云主机安全组打开8181端口`

- 拉取mysql镜像

  1. 创建容器，设置端口映射、目录映射

  ```shell
  # 创建mysql目录用于存储mysql数据信息
  mkdir ~/mysql
  cd ~/mysql
  ```

  ```shell
  docker run -id \
  -p 3306:3306 \
  --name=c_mysql \
  -v $PWD/conf:/etc/mysql/conf.d \
  -v $PWD/logs:/logs \
  -v $PWD/data:/var/lib/mysql \
  -e MYSQL_ROOT_PASSWORD=123456 \
  mysql
  ```

  2. 参数说明：

  - **-p 3306:3306**：将容器的 3306 端口映射到宿主机的 3306 端口。
  - **-v $PWD/conf:/etc/mysql/conf.d**：将主机当前目录下的 conf/my.cnf 挂载到容器的 /etc/mysql/my.cnf。配置目录
  - **-v $PWD/logs:/logs**：将主机当前目录下的 logs 目录挂载到容器的 /logs。日志目录
  - **-v $PWD/data:/var/lib/mysql** ：将主机当前目录下的data目录挂载到容器的 /var/lib/mysql 。数据目录
  - **-e MYSQL_ROOT_PASSWORD=123456：**初始化 root 用户的密码。

  3. 进入容器，操作mysql

  ```shell
  docker exec –it c_mysql /bin/bash
  ```

  4. 阿里云安全组开发 3306端口
  5. 使用外部机器连接容器中的mysql
  6. 本地数据库迁移

- 拉取redis镜像

  1. 创建容器，设置端口映射

  ```shell
  docker run -id --name=c_redis -p 6379:6379 redis
  ```

  2. 使用外部机器连接redis
  3. 阿里云安全组开发 6379端口
  4. 本地redis数据迁移



<br/>

<br/>

<br/>

<br/>

### 3.vue部署至docker流程

- 项目打包

  ```
  npm run build
  ```

- 本机部署nginx测试（可省略）

  - 打开nginx > conf > nginx.conf

  - 在nginx.conf文件中修改如下

    ```conf
    server {
            listen       80; 
            server_name  localhost;
            location / {
            	# path 处填写vue项目打包dist文件夹的绝对路径，也可以不修改，但要将dist文件夹下内容复制到 nginx > html文件夹下
                root   <path>;
                try_files $uri $uri/ /index.html;
                index  index.html index.htm;
            }
     }
    ```

  - 点击nginx.exe，浏览器输入locahost

    !> 千万记住，后台只能有一个nginx.exe程序运行，若启动多个，请杀死它们

- docker部署

  - docker拉取nginx镜像

  - xftp将dist文件夹传到/usr/share目录下

  - 同目录下编写dockerfile文件

    ```
    # 设置基础镜像
    FROM nginx
    # 定义作者
    MAINTAINER byxlr
    # 将dist文件中的内容复制到 /usr/share/nginx/html/ 这个目录下面
    COPY dist/  /usr/share/nginx/html/
    COPY nginx.conf /etc/nginx/nginx.conf
    RUN echo 'echo init ok!'
    ```

  - 同目录下编写nginx.conf，将windows下nginx.conf复制过来，修改如下：

    ```
    server {
            listen       80;
            server_name  location;
             location / {
                root   /usr/share/nginx/html;
                index  index.html index.htm;
                try_files $uri $uri/ /index.html;
             }
     }
    ```

  - cd 进入share目录，docker build -t <镜像名> 构建镜像

  - 运行生成成功的镜像：docker run -p 3000:80 -d <镜像名>

    `阿里云安全组开启3000端口`

  - 启动浏览器输入： 云主机ip:3000

