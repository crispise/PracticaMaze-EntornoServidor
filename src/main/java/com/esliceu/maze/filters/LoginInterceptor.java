package com.esliceu.maze.filters;

import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    UserDAO userDAO;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");
        User user = userDAO.getUserByUsername(username);
        System.out.println(" estado del usuario" + user.getState());
        if (username == null || user.getState().equals("disconect")) {
            System.out.println("entra en usuario null o usuario disconect");
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
