import jsCookie from 'js-cookie';
export function getToken() {
  return jsCookie.get('Authorization')
}

export function getRole() {
  return String(getToken()).charAt(String(getToken()).length-1)
}

export function setUser(user) {
  return jsCookie.set('user',user)
}


