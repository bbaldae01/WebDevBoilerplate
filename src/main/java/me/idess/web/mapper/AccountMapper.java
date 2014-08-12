package me.idess.web.mapper;

import me.idess.web.model.vo.AccountVO;

public interface AccountMapper {
	
	AccountVO selectAccountByUsername(String userid);
	
}
