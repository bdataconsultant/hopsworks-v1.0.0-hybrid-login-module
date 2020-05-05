package it.almaviva.giotto.bigdata.ldap;

import com.sun.enterprise.util.i18n.StringManager;
import org.glassfish.security.common.FileRealmHelper;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class CustomLDAPFileAuthenticator {

    private StringManager sm;

    private FileRealmHelper helper;

    public CustomLDAPFileAuthenticator(String file, StringManager sm) throws LoginException {
        this.sm = sm;
        init(file);
    }

    public String[] authenticate(String user, char[] password)
    {
       return helper.authenticate(user, password);
    }

    private void init(String file) throws LoginException {
        if (file == null) {
            throw new LoginException("No key file property specified");
        }
        try {
            helper = new FileRealmHelper(file);
        } catch (IOException ioe) {
            String msg = sm.getString("Key file access error", ioe.toString());
            throw new LoginException(msg);
        }
    }
}
