package com.cloud.user;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.naming.ConfigurationException;

import com.cloud.acl.ControlledEntity;
import com.cloud.acl.SecurityChecker.AccessType;
import com.cloud.api.commands.DeleteUserCmd;
import com.cloud.api.commands.RegisterCmd;
import com.cloud.api.commands.UpdateAccountCmd;
import com.cloud.api.commands.UpdateUserCmd;
import com.cloud.domain.Domain;
import com.cloud.exception.ConcurrentOperationException;
import com.cloud.exception.PermissionDeniedException;
import com.cloud.exception.ResourceUnavailableException;
import com.cloud.utils.Pair;
import com.cloud.utils.component.Manager;


@Local(value = { AccountManager.class, AccountService.class })
public class MockAccountManagerImpl implements Manager, AccountManager {


    @Override
    public boolean deleteUserAccount(long accountId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public UserAccount disableUser(long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserAccount enableUser(long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserAccount lockUser(long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserAccount updateUser(UpdateUserCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account disableAccount(String accountName, Long domainId) throws ConcurrentOperationException, ResourceUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account enableAccount(String accountName, long domainId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account lockAccount(String accountName, Long domainId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account updateAccount(UpdateAccountCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account getSystemAccount() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getSystemUser() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteUser(DeleteUserCmd deleteUserCmd) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAdmin(short accountType) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Account finalizeOwner(Account caller, String accountName, Long domainId, Long projectId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pair<List<Long>,Long> finalizeAccountDomainForList(Account caller, String accountName, Long domainId, Long projectId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account getActiveAccountByName(String accountName, Long domainId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account getActiveAccountById(Long accountId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account getAccount(Long accountId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getActiveUser(long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getUser(long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isRootAdmin(short accountType) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User getActiveUserByRegistrationToken(String registrationToken) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void markUserRegistered(long userId) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean disableAccount(long accountId) throws ConcurrentOperationException, ResourceUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteAccount(AccountVO account, long callerUserId, Account caller) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void checkAccess(Account account, Domain domain) throws PermissionDeniedException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean cleanupAccount(AccountVO account, long callerUserId, Account caller) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public UserVO createUser(String userName, String password, String firstName, String lastName, String email, String timeZone, String accountName, Long domainId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long checkAccessAndSpecifyAuthority(Account caller, Long zoneId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        return true;
    }

    @Override
    public boolean start() {
        return true;
    }

    @Override
    public boolean stop() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void checkAccess(Account account, AccessType accessType, ControlledEntity... entities) throws PermissionDeniedException {
        // TODO Auto-generated method stub
    }

    @Override
    public void logoutUser(Long userId) {
        // TODO Auto-generated method stub
    }

    @Override
    public UserAccount getUserAccount(String username, Long domainId) {
        return null;
    }

    @Override
    public UserAccount authenticateUser(String username, String password, Long domainId, Map<String, Object[]> requestParameters) {
        return null;
    }

    @Override
    public Pair<User, Account> findUserByApiKey(String apiKey) {
        return null;
    }

    @Override
    public UserVO createUser(long accountId, String userName, String password, String firstName, String lastName, String email, String timezone) {
        return null;
    }

    @Override
    public String[] createApiKeyAndSecretKey(RegisterCmd cmd) {
        return null;
    }

    @Override
    public boolean lockAccount(long accountId) {
        return true;
    }


	@Override
	public UserAccount createUserAccount(String userName, String password,
			String firstName, String lastName, String email, String timezone,
			String accountName, short accountType, Long domainId,
			String networkDomain, Map details) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account createAccount(String accountName, short accountType,
			Long domainId, String networkDomain, Map details) {
		// TODO Auto-generated method stub
		return null;
	}

}
