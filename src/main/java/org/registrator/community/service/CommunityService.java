package org.registrator.community.service;

import java.util.List;

import org.registrator.community.dto.CommunityDTO;
import org.registrator.community.entity.TerritorialCommunity;

public interface CommunityService {

   List<TerritorialCommunity> findAll();
   List<TerritorialCommunity> findAllByAsc();
   TerritorialCommunity findByName(String name);
   TerritorialCommunity addCommunity (TerritorialCommunity territorialCommunity);
   boolean deleteCommunity(TerritorialCommunity territorialCommunity);
   TerritorialCommunity findById(Integer id);
   boolean updateCommunity(CommunityDTO communityDTO);
}
