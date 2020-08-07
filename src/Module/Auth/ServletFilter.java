package Module.Auth;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class ServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String path = httpServletRequest.getRequestURL().toString();
        String urlEncode = URLEncoder.encode(path, "UTF-8");
        if (path.contains("/api/authentication/") || path.contains("/Login.html") || path.contains("/Angular4/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.sendRedirect("/Login.html?redirect=" + urlEncode);
            return;
        }
//        Cookie[] cookies = httpServletRequest.getCookies();
//        if(cookies == null){
//            httpServletResponse.sendRedirect("/Login.html?redirect="+ urlEncode);
//            return;
//        }
//        String tokenKey = null;
//        for (Cookie cookie : cookies) {
//            if ("auth-tokenKey".equals(cookie.getName())) {
//                tokenKey = cookie.getValue();
//            }
//        }
//        if (tokenKey == null || tokenKey.trim().equals("")) {
//            httpServletResponse.sendRedirect("/Login.html?redirect="+ urlEncode);
//            return;
//        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
