package com.Project.Scope.model;

public class PasswordForm {
	private String existingPassword;
    private String newPassword;
	public String getExistingPassword() {
		return existingPassword;
	}
	public void setExistingPassword(String existingPassword) {
		this.existingPassword = existingPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public PasswordForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public PasswordForm(String existingPassword, String newPassword) {
		super();
		this.existingPassword = existingPassword;
		this.newPassword = newPassword;
	}


}
