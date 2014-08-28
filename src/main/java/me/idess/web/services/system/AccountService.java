package me.idess.web.services.system;

import java.util.List;

import me.idess.web.controller.AuthenticationController;
import me.idess.web.mapper.AccountMapper;
import me.idess.web.model.vo.AccountVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	
	private static final Logger	logger	= LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	MessageSource				messageSource;
	@Autowired
	AccountMapper				accountMapper;
	
	public List<AccountVO> selectAccounts() {
		logger.debug("AccountService selectAccounts method");
		List<AccountVO> accountVOs = accountMapper.selectAccounts();
		return accountVOs;
	}
	
	public AccountVO selectAccountByUsername(String username) {
		logger.debug("AccountService selectAccounts method");
		AccountVO accountVO = accountMapper.selectAccountByUsername(username);
		return accountVO;
	}
}
