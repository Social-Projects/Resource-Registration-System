package org.registrator.community.dao;

import org.registrator.community.entity.PassportInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<PassportInfo, Integer> {

}
