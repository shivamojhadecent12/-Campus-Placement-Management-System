/* Auth State Management & Helpers */

class AuthManager {
  static setCurrentUser(user) {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  static getCurrentUser() {
    const data = localStorage.getItem('currentUser');
    return data ? JSON.parse(data) : null;
  }

  static clearCurrentUser() {
    localStorage.removeItem('currentUser');
  }

  static isAuthenticated(requiredRole = null) {
    const user = this.getCurrentUser();
    if (!user) return false;
    if (requiredRole && user.role !== requiredRole) return false;
    return true;
  }

  static requireAuth(requiredRole, redirectUrl = '/index.html') {
    if (!this.isAuthenticated(requiredRole)) {
      window.location.href = redirectUrl;
    }
  }

  static logout() {
    this.clearCurrentUser();
    window.location.href = '/index.html';
  }
}
