package com.unimib.KanbanBoard.service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.unimib.KanbanBoard.dto.SignUpDto;
import com.unimib.KanbanBoard.dto.UserInfoDto;
import com.unimib.KanbanBoard.model.Colonna;
import com.unimib.KanbanBoard.model.KanbanContext;
import com.unimib.KanbanBoard.model.Login;
import com.unimib.KanbanBoard.model.Tile;
import com.unimib.KanbanBoard.model.User;
import com.unimib.KanbanBoard.repository.KanbanContextDao;
import com.unimib.KanbanBoard.repository.UserDao;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private KanbanContextDao kanbanContextDao;

	@Autowired
	private UserDao userDao;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private static boolean createdNewAdmin;

	public UserInfoDto getUserInfoArchiviate(String username) {
		return makeUserInfoArchiviateDto(findUserByUsername(username));
	}

	public UserInfoDto getUserInfo(String username) {
		return makeUserInfoDto(findUserByUsername(username));
	}

	@Override
	public String saveUser(SignUpDto signUpDto) throws Exception {
		// TODO Auto-generated method stub	


		// checks if there is any admin
		List<KanbanContext> kcList = kanbanContextDao.findAll();

		bCryptPasswordEncoder = new BCryptPasswordEncoder();

		signUpDto.setPassword(bCryptPasswordEncoder.encode(signUpDto.getPassword()));

		// save the user
		User user = userDao.save(userCreationStrategy(kcList, signUpDto));

		if (createdNewAdmin) {
			kanbanContextDao.save(new KanbanContext(true));
		}

		return user.getUsername();
	}

	public static User userCreationStrategy(List<KanbanContext> kcList, SignUpDto signUpDto) {

		User user = null;		

		if (kcList.size() == 0) {

			// no context availabe => no admin => create admin
			user = makeUser(signUpDto, new Login(), new User(), true);

			createdNewAdmin = true;

		} else {

			//context available

			KanbanContext context = kcList.get(0);

			if (context.isExistAdimn()) {

				// context available but admin exists => normal
				user = makeUser(signUpDto, new Login(), new User(), false);
				createdNewAdmin = false;

			} else {

				// context available but admin does not exist => create admin
				user = makeUser(signUpDto, new Login(), new User(), true);
				createdNewAdmin = true;
			}
		}
		System.out.println(user.isAdmin());
		return user;
	}


	public static User makeUser(SignUpDto signUpDto, Login login, User user, boolean isAdmin) {

		login.setPassword(signUpDto.getPassword());
		login.setUsername(signUpDto.getUsername());
		login.setTimestampLogin(new Date(0));
		login.setTimestampLogout(new Date(0));
		user.setAdmin(isAdmin);

		user.setEmail(signUpDto.getEmail());
		user.setUsername(signUpDto.getUsername());
		user.setLogin(login);


		return user;

	}

	@Override
	public User findUserByUsername(String userName) {
		// TODO Auto-generated method stub
		return userDao.findByUsername(userName);
	}

	private static UserInfoDto makeUserInfoDto(User user) {

		UserInfoDto userInfoDto = new UserInfoDto();

		userInfoDto.setAdmin(user.isAdmin());


		System.out.println(user.getColonneSet().size());


		userInfoDto.setColonneSet(user.getColonneSet().stream()
				.filter(x -> ! x.isArchiviato())
				.collect(Collectors.toSet()));

		System.out.println(userInfoDto.getColonneSet().size());

		userInfoDto.setEmail(user.getEmail());

		userInfoDto.setUsername(user.getUsername());


		return userInfoDto;
	}

	private static UserInfoDto makeUserInfoArchiviateDto(User user) {

		UserInfoDto userInfoDto = new UserInfoDto();

		userInfoDto.setAdmin(user.isAdmin());


		System.out.println(user.getColonneSet().size());


		userInfoDto.setColonneSet(user.getColonneSet().stream()
				.filter(x -> x.isArchiviato())
				.collect(Collectors.toSet()));

		System.out.println(userInfoDto.getColonneSet().size());

		userInfoDto.setEmail(user.getEmail());

		userInfoDto.setUsername(user.getUsername());


		return userInfoDto;
	}

	@Override
	public boolean deleteUser(Long userId) {
		// TODO Auto-generated method stub

		userDao.deleteById(userId);

		return true;
	}

	@Override
	public boolean deleteContext() {
		// TODO Auto-generated method stub

		kanbanContextDao.deleteAll();

		return true;
	}

	@Override
	public UserInfoDto getUserInfoAll(String username) {
		// TODO Auto-generated method stub
		return makeUserInfoAllDto(findUserByUsername(username));
	}
	
	private static UserInfoDto makeUserInfoAllDto(User user) {

		UserInfoDto userInfoDto = new UserInfoDto();

		userInfoDto.setAdmin(user.isAdmin());


		System.out.println(user.getColonneSet().size());


		userInfoDto.setColonneSet(user.getColonneSet());

		System.out.println(userInfoDto.getColonneSet().size());

		userInfoDto.setEmail(user.getEmail());

		userInfoDto.setUsername(user.getUsername());


		return userInfoDto;
	}


}
