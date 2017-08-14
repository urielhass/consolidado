package br.com.stock.exception;

public class UserPermissionException extends MainException{

	private static final long serialVersionUID = 1L;

	public UserPermissionException(){
		this.generateObject("Vocë não tem acesso para alterar o usuário.", "Somente o administrador pode fazer isso.");
	}
}
