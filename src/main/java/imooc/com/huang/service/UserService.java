package imooc.com.huang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import imooc.com.huang.dao.UserDao;
import imooc.com.huang.domain.User;

@Service
public class UserService {
    
	@Autowired
	UserDao userDao;
	
	public User getById(int id) {
    	  return userDao.getById(id);
      }
    @Transactional
	public boolean tx() {
		// TODO Auto-generated method stub
	    User user1 = new User();
	    User user2 = new User();
	    user1.setId(2);
	    user1.setName("zhang");
	    userDao.Insert(user1);
//	    user2.setId(1);
//	    user2.setName("llll");
//	    userDao.Insert(user2);
	    return true;
	}
}
