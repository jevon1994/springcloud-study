package cn.leon.auth.filter;

import cn.leon.auth.confirm.SelfAuthenticationToken;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    protected LoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected LoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String type = getParamater(request, "type");

        String principal = null;
        if ("qr".equals(type)) {
            principal = getParamater(request, "qrCode");
        }
        SelfAuthenticationToken selfAuthenticationToken = new SelfAuthenticationToken(principal,
                null, "qr", "00000");
        selfAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));

        return this.getAuthenticationManager().authenticate(selfAuthenticationToken);
    }

    private String getParamater(HttpServletRequest request, String type) {
        return request.getParameter(type);
    }
}
