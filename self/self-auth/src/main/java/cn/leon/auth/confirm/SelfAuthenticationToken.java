package cn.leon.auth.confirm;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class SelfAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 110L;
    private final Object principal;
    private Object credentials;
    private String type;
    private String mobile;

    public SelfAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.type = type;
        this.mobile = mobile;
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
