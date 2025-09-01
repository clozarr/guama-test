package com.guama.purchases.shared.mapper;

import com.guama.purchases.app.dto.PurchaseDetailRequestDto;
import com.guama.purchases.app.dto.PurchaseDetailResponseDto;
import com.guama.purchases.app.dto.PurchaseRequestDto;
import com.guama.purchases.app.dto.PurchaseResponseDto;
import com.guama.purchases.domain.entity.Purchase;
import com.guama.purchases.domain.entity.PurchaseDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PurchaseMapper {

    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    @Mapping(target = "totalPrice", source = "price")
    PurchaseResponseDto toPurchaseResponseDto(Purchase purchase);
    @Mapping(target = "price", source = "totalPrice")
    @Mapping(target = "purchaseId", ignore = true)
    Purchase toPurchase(PurchaseRequestDto purchaseRequestDto);


    List<PurchaseDetail>  toPurchaseDetails(List<PurchaseDetailRequestDto> detailsDto);
    List<PurchaseDetailResponseDto> toPurchaseDetailsResponseDto(List<PurchaseDetail> details);

    PurchaseDetail toPurchaseDetail(PurchaseDetailRequestDto detailDto);
    PurchaseDetailResponseDto toPurchaseDetailResponse(PurchaseDetail detail);


}
