package me.idess.web.mapper;

import java.util.List;

import me.idess.web.model.vo.AccountVO;

public interface AccountMapper {
	
	AccountVO selectAccountByUsername(String userid);
	
	List<AccountVO> selectAccounts();
}
