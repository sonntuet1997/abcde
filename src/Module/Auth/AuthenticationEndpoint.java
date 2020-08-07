package Module.Auth;


import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/authentication")
public class AuthenticationEndpoint {
//    @Inject
//    private UserService userService;
//    @Inject
//    private TokenService tokenService;

//    @POST
//    @Path("/login")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response authenticateUserJson(Credentials credentials) {
//        try {
    // Authenticate the user using the credentials provided
//            UserEntity userEntity = authenticate(credentials.getUsername(), credentials.getPassword());
    // Issue a token for the user
//            String token = issueToken(userEntity);
//            NewCookie cookie = new NewCookie("auth-tokenKey", token, "/", "", "comment", 3600 * 24 * 30, false);
    // Return the token on the response
//            return Response.ok(token).cookie(cookie).build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
//    }

//    @POST
//    @Path("/login")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response authenticateUser(@FormParam("username") String username,
//                                     @FormParam("password") String password) {
//        try {
    // Authenticate the user using the credentials provided
//            UserEntity userEntity = authenticate(username, password);
    // Issue a token for the user
//            String token = issueToken(userEntity);
//            NewCookie cookie = new NewCookie("auth-tokenKey", token, "/", "", "comment", 3600 * 24 * 30, false);
    // Return the token on the response
//            return Response.ok("{\"auth-tokenKey\":\"" + token + "\"}").cookie(cookie).build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
//    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@CookieParam("auth-tokenKey") Cookie cookie) throws URISyntaxException {
        if (cookie != null) {
//            TokenEntity tokenEntity = tokenService.getByTokenKey(cookie.getValue());
//            if (tokenEntity != null) tokenService.delete(tokenEntity.id);
            NewCookie newCookie = new NewCookie("auth-tokenKey", "", "/", "", "comment", 0, false);
            return Response.temporaryRedirect(new URI("/Login.html")).cookie(newCookie).build();
        }
        return Response.temporaryRedirect(new URI("/Login.html")).build();
    }

    @GET
    @Path("/logout/{auth-tokenKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@PathParam("auth-tokenKey") String tokenKey) {
        if (tokenKey != null) {
//            TokenEntity tokenEntity = tokenService.getByTokenKey(tokenKey);
//            if (tokenEntity != null) tokenService.delete(tokenEntity.id);
            NewCookie newCookie = new NewCookie("auth-tokenKey", "", "/", "", "comment", 0, false);
            return Response.ok("OK").cookie(newCookie).build();
        }
        return Response.ok("OK - No session").build();
    }

//    private UserEntity authenticate(String username, String password) throws Exception {
//        UserEntity userEntity = userService.getByUserName(username);
//        if (userEntity == null || !userEntity.passWord.equals(password))
//            throw new CustomException(400,"Tên đăng nhập hoặc mật khẩu không đúng");
//        else return userEntity;
//    }

//    private String issueToken(UserEntity userEntity) {
//        Random random = new SecureRandom();
//        String tokenKey = new BigInteger(130, random).toString(32);
//        TokenEntity tokenEntity = new TokenEntity();
//        tokenEntity.userId = userEntity.id;
//        tokenEntity.tokenKey = tokenKey;
//        tokenEntity.expriedTime = Timestamp.valueOf(LocalDateTime.now().plusMonths(1));
//        tokenService.encryptAndHash(tokenEntity);
//        return tokenKey;
//    }
}
