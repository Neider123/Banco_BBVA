package co.empresa.bbva.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import co.empresa.bbva.dao.UsuarioDao;
import co.empresa.bbva.modelo.User;


/**
 * Servlet implementation class UsuarioServlet
 */

public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UsuarioDao usuarioDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.usuarioDao = new UsuarioDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
				String action = request.getServletPath();
				try {
				switch(action) {
				case "/new":
					showNewForm(request, response);
					break;
				case "/insert":
					insertarUsuario(request, response);
					break;
				case "/delete":
					eliminarUsuario(request, response);
					break;
				case "/edit":
					showEditForm(request, response);
					break;
				case "/update":
					actualizarUsuario(request, response);
					break;
				default :
					listarUsuarios(request, response);
					break;
					
				}
				}catch(SQLException e) {
					throw new ServletException(e);
				}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("usuario.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, SQLException, IOException {
	
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");
		
		User usuario = new User(username, pass, email);
		
		usuarioDao.insert(usuario);
		
		response.sendRedirect("list");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		
		User usuarioActual = usuarioDao.select(id);
		
		request.setAttribute("usuario", usuarioActual);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("usuario.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");
		
		User usuario = new User(id, username, pass, email);
		
		usuarioDao.update(usuario);
		
		response.sendRedirect("list");
	}
	
	private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		 	
		usuarioDao.delete(id);
		
		response.sendRedirect("list");
	}
	
	private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, SQLException, IOException {
		List<User> listUsuarios = usuarioDao.selectAll();
		request.setAttribute("listUsuarios", listUsuarios);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("usuariolist.jsp");
		dispatcher.forward(request, response);
	}
	
	private void validarsilogin(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, SQLException, IOException {
	    String username = request.getParameter("username");
	    String pass = request.getParameter("pass");

	    boolean esValido = usuarioDao.validarLogin(username, pass);

	    if (esValido) {
	        response.sendRedirect("bills.jsp");
	    } else {
	        // Si las credenciales no son válidas, redirige a la página de error
	        response.sendRedirect("pagina_error.jsp");
	    }
	}


}
