package com.Moses.Moses.POS.System.service.branch;

import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.payload.dto.branchDto.BranchDTO;

import java.util.List;

public interface BranchService {
    BranchDTO createBranch(BranchDTO branchDTO, User user) throws UserException;
    BranchDTO updateBranch(Long id, BranchDTO branchDTO, User user) throws Exception;
    void deleteBranch(Long id) throws Exception;
    List<BranchDTO> getAllBranchesByStoreId(Long storeId);
    BranchDTO getBranchById(Long id) throws Exception;
}
