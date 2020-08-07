package Module.Auth;

import com.mysql.cj.core.util.StringUtils;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Strings.isNullOrEmpty;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
//        String path = requestContext.getUriInfo().getPath();
//        if (path.startsWith("authentication/")) return;
//        if (requestContext.getRequest().getMethod().equals("OPTIONS")) {
//            requestContext.abortWith(Response.status(Response.Status.OK).build());
//            return;
//        }
//        Cookie cookieTokenKey = requestContext.getCookies().get("auth-tokenKey");
        // Then check is the service key exists and is valid.
//        if (cookieTokenKey == null) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//            return;
//        }
//        String tokenKey = cookieTokenKey.getValue();
//        if (tokenKey == null || tokenKey.trim().equals("")) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//            return;
//        }
//        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//        employeeEntity.groupEntity

        // For any pther methods besides login, the authToken must be verified
//        if (!path.startsWith("/demo-business-resource/login/")) {
//            String authToken = requestCtx.getHeaderString(DemoHTTPHeaderNames.AUTH_TOKEN);
//
//            // if it isn't valid, just kick them out.
//            if (!demoAuthenticator.isAuthTokenValid(serviceKey, authToken)) {
//                requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//            }
//        }
    }

    /**
     * Perform authorization based on roles.
     *
     * @param rolesAllowed
     * @param requestContext
     */
    private void performAuthorization(String[] rolesAllowed,
                                      ContainerRequestContext requestContext) throws AccessDeniedException {

        if (rolesAllowed.length > 0 && !isAuthenticated(requestContext)) {
            refuseRequest();
        }

        for (final String role : rolesAllowed) {
            if (requestContext.getSecurityContext().isUserInRole(role)) {
                return;
            }
        }

        refuseRequest();
    }

    /**
     * Check if the user is authenticated.
     *
     * @param requestContext
     * @return
     */
    private boolean isAuthenticated(final ContainerRequestContext requestContext) {
        // Return true if the user is authenticated or false otherwise
        // An implementation could be like:
        // return requestContext.getSecurityContext().getUserPrincipal() != null;
        return true;
    }

    /**
     * Refuse the request.
     */
    private void refuseRequest() throws AccessDeniedException {
        throw new AccessDeniedException(
                "You don't have permissions to perform this action.");
    }

    private String parseLink(String link) {
        List<String> linkParts = Arrays.stream(link.split("/")).filter(x -> !isNullOrEmpty(x)).collect(Collectors.toList());
        for (int i = 0; i < linkParts.size(); i++) {
            if (StringUtils.isStrictlyNumeric(linkParts.get(i)))
                linkParts.set(i, "*");
        }
        return String.join("/", linkParts);
    }
}