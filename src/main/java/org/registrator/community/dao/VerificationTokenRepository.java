package org.registrator.community.dao;

import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
	
	@Query("select t from VerificationToken t where t.userEmail =:userEmail")
	public VerificationToken findTokenByEmail(@Param("userEmail") String userEmail);
	
	@Query("select t from VerificationToken t where t.token =:token")
	public VerificationToken findVerificationTokenByToken(@Param("token") String token);

	@Query("select t from VerificationToken t where t.token =:token and t.tokenType=:tokenType")
	public VerificationToken findVerificationTokenByTokenAndTokenType(
			@Param("token") String token, @Param("tokenType") TokenType tokenType);
	
	@Query("select t from VerificationToken t where t.userLogin =:login and t.tokenType=:tokenType")
    public VerificationToken findVerificationTokenByLoginAndTokenType(
            @Param("login") String token, @Param("tokenType") TokenType tokenType);
	
}
