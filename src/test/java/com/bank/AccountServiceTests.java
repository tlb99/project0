package com.bank;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bank.dao.AccountDao;
import com.bank.exceptions.RegisterAccountFailedException;
import com.bank.exceptions.RegisterUserFailedException;
import com.bank.models.Account;
import com.bank.models.Role;
import com.bank.models.User;
import com.bank.models.Account;
import com.bank.service.AccountService;
import com.bank.service.UserService;

public class AccountServiceTests {
	
	// Declare a variable of the class to be tested
	private AccountService as;
	
	// Declare the dependencies of the class to be tested
	private AccountDao mockDao;
	
	// Let's define a stub
	private Account dummyAccount;
	
	@Before
	public void setup() {
		as = new AccountService();
		
		// Let's mock the class that's being injected
		mockDao = mock(AccountDao.class);
		
		// Let's set the user dao inside of the userservice to this mocked one
		as.aDao = mockDao;
		
		// Let's also set up a stub user for passing in and stuff
		dummyAccount = new Account();
		dummyAccount.setId(0);
	}
	
	// Let's create a teardown method to just reset what we had
	@After
	public void teardown() {
		
		as = null;
		dummyAccount = null;
		mockDao = null;
	}
	
	@Test
	public void testRegisterAccountReturnsNewPKId() {
		dummyAccount = new Account(0, 0.0, 1, true);
		
		// Let's generate a random number to pretend the DB created it
		Random r = new Random();
		int fakePK = r.nextInt(100);
		
		// So now before we run the actual user service method we have to set up a mock
		// to emulate the function of the insert method from the dao
		
		when(mockDao.insert(dummyAccount)).thenReturn(fakePK);
		
		// The registered user of our register method SHOULD have the id that's returned from insert
		Account a = as.register(dummyAccount);
		
		// The final thing to do is call our assert method and the id of the account
		// to see if it matches the fakePK
		assertEquals(a.getId(), fakePK);
	}
	
	@Test(expected = RegisterAccountFailedException.class)
	public void testRegisterAccountWithNonZeroId() {
		
		dummyAccount.setId(1);
		
		as.register(dummyAccount); // This should throw an exception before insert ever gets off because of our data validation
	}
	
	// Let's test our other branch to be thorough
	// This one comes after insert so we'll have to mock it again
	@Test(expected = RegisterAccountFailedException.class)
	public void testInsertedAccountReturnedNegativeOne() {
		
		// Dummy user we're passing to the insert method
		dummyAccount = new Account(0, 0.0, 1, true);
		
		// Returning this value from the insert method to cause an exception
		int fakePK = -1;
		
		// Mock insert method and return fakePK
		when(mockDao.insert(dummyAccount)).thenReturn(fakePK);
		
		// This should return an exception
		Account acc = as.register(dummyAccount);

	}
	
	@Test
	public void testFindByOwnerIdReturnsList() {
		List<Account> accList = new LinkedList<Account>();
		
		dummyAccount = new Account(0, 0.0, 1, true);
		accList.add(dummyAccount);
		dummyAccount = new Account(1, 0.0, 1, true);
		accList.add(dummyAccount);
		dummyAccount = new Account(2, 0.0, 1, true);
		accList.add(dummyAccount);
		
		
		when(mockDao.findByOwner(1)).thenReturn(accList);
		
		assertEquals(as.returnAccountsByOwnerId(1), accList);
	}
	
	@Test
	public void testReturnApplicationsReturnsQueue() {
		Queue<Account> accQueue = new LinkedList<Account>();
		
		dummyAccount = new Account(0, 0.0, 1, true);
		accQueue.add(dummyAccount);
		dummyAccount = new Account(1, 0.0, 1, true);
		accQueue.add(dummyAccount);
		dummyAccount = new Account(2, 0.0, 1, true);
		accQueue.add(dummyAccount);
		
		
		when(mockDao.findByIsActive()).thenReturn(accQueue);
		
		assertEquals(as.returnApplications(), accQueue);
	}
	
	@Test
	public void testReturnApplicationsReturnsNull() {
		Queue<Account> accQueue = null;
		
		
		when(mockDao.findByIsActive()).thenReturn(accQueue);
		
		assertEquals(as.returnApplications(), null);
	}
	
	@Test
	public void testUpdateAccountReturnsTrue() {
		dummyAccount = new Account(0, 0.0, 1, true);
		
		when(mockDao.update(dummyAccount)).thenReturn(true);
		
		assertEquals(mockDao.update(dummyAccount), true);
	}
	
	@Test
	public void testUpdateAccountReturnsFalse() {
		dummyAccount = new Account(0, 0.0, 1, true);
		
		when(mockDao.update(dummyAccount)).thenReturn(false);
		
		assertEquals(as.updateAccount(dummyAccount), false);
	}
	
	@Test
	public void testDeleteAccountReturnsTrue() {
		dummyAccount = new Account(0, 0.0, 1, true);
		
		when(mockDao.delete(dummyAccount)).thenReturn(true);
		
		assertEquals(as.deleteAccount(dummyAccount), true);
	}
	
	@Test
	public void testDeleteAccountReturnsFalse() {
		dummyAccount = new Account(0, 0.0, 1, true);
		
		when(mockDao.delete(dummyAccount)).thenReturn(false);
		
		assertEquals(as.deleteAccount(dummyAccount), false);
	}
	
	@Test
	public void testGetAccountByIdReturnsAccount() {
		dummyAccount = new Account(0, 0.0, 1, true);
		
		when(mockDao.findById(0)).thenReturn(dummyAccount);
		
		assertEquals(as.getAccountByID(dummyAccount.getId()), dummyAccount);
	}
}
