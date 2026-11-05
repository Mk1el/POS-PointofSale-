package com.Moses.Moses.POS.System.service.refund;

import com.Moses.Moses.POS.System.modal.refundModal.Refund;
import com.Moses.Moses.POS.System.payload.dto.refundDto.RefundDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundService {
    RefundDTO createRefund(RefundDTO refund) throws Exception;
    List<RefundDTO> getAllRefunds() throws Exception;
    List<RefundDTO> getRefundByCashier(Long cashierId) throws Exception;
    List<RefundDTO> getRefundByShiftReport(Long shiftReportId) throws Exception;
    List<RefundDTO> getRefundByCashierAndDateRange(Long cashier, LocalDateTime startDate, LocalDateTime endDate)throws Exception;
    List<RefundDTO> getRefundByBranch(Long branchId) throws Exception;
    RefundDTO getRefundById(Long refundId) throws Exception;
    void deleteRefund(Long refundId) throws Exception;

}
