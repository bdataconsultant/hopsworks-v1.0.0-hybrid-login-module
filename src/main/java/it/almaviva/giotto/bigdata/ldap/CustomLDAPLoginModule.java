package it.almaviva.giotto.bigdata.ldap;

import com.sun.enterprise.security.auth.login.PasswordLoginModule;

import javax.security.auth.login.LoginException;

public class CustomLDAPLoginModule extends PasswordLoginModule
{
    private CustomLDAPRealm _ldapRealm;

    /**
     * Performs authentication for the current user.
     *
     */
    protected void authenticate ()
            throws LoginException
    {
        if (!(_currentRealm instanceof CustomLDAPRealm)) {
            String msg = sm.getString("ldaplm.badrealm");
            throw new LoginException(msg);
        }
        _ldapRealm = (CustomLDAPRealm)_currentRealm;

        // enforce that password cannot be empty.
        // ldap may grant login on empty password!
        if (getPasswordChar() == null || getPasswordChar().length == 0) {
            String msg = sm.getString("ldaplm.emptypassword", _username);
            throw new LoginException(msg);
        }

        String[] grpList = null;

        // try service account login first
        String file = _currentRealm.getProperty(CustomLDAPRealm.PARAM_KEYFILE);
        CustomLDAPFileAuthenticator fileRealm = new CustomLDAPFileAuthenticator(file, sm);
        grpList = fileRealm.authenticate(getUsername(), getPasswordChar());

        if (grpList == null) {
            // in case not found try common user login
            String mode = _currentRealm.getProperty(CustomLDAPRealm.PARAM_MODE);
            if (CustomLDAPRealm.MODE_FIND_BIND.equals(mode)) {
                grpList = _ldapRealm.findAndBind(_username, getPasswordChar());
            } else {
                String msg = sm.getString("ldaplm.badmode", mode);
                throw new LoginException(msg);
            }
        }

        commitAuthentication(_username, getPasswordChar(),
                _currentRealm, grpList);
    }
}
