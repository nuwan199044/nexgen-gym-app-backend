package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.criteria.MemberCriteria;
import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.dto.request.MemberRequest;
import com.enzith.nexgen.dto.response.MemberResponse;
import com.enzith.nexgen.entity.Member;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.exception.MemberException;
import com.enzith.nexgen.repository.MemberRepository;
import com.enzith.nexgen.service.MemberService;
import com.enzith.nexgen.specification.MemberSpecification;
import com.enzith.nexgen.utility.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public String createMember(MemberRequest memberRequest) {
        validateMemberEmail(memberRequest.getEmail());

        Member member = modelMapper.map(memberRequest, Member.class);
        member = memberRepository.save(member);

        return member.getMembershipNo();
    }

    @Override
    @Transactional
    public String updateMember(MemberRequest memberRequest) {
        Member existingMember = memberRepository.findById(memberRequest.getMemberId())
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBER_NOT_FOUND));

        validateUpdatedEmail(existingMember, memberRequest.getEmail());

        modelMapper.map(memberRequest, existingMember);
        memberRepository.save(existingMember);

        return existingMember.getMembershipNo();
    }

    @Override
    public Map<String, Object> findAllMembers(String firstName, String phoneNo, Integer currentPage, Integer pageSize) {
        MemberCriteria criteria = MemberCriteria.builder()
                .firstName(firstName)
                .phoneNo(phoneNo)
                .build();

        PaginationCriteria paginationCriteria = PaginationCriteria.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();

        Pageable pageable = PaginationUtils.getPage(paginationCriteria);
        Page<Member> members = memberRepository.findAll(new MemberSpecification(criteria), pageable);
        return PaginationUtils.convertToPagination(members.map(member -> modelMapper.map(member, MemberResponse.class)));
    }

    private void validateUpdatedEmail(Member existingMember, String newEmail) {
        if (!existingMember.getEmail().equals(newEmail)) {
            validateMemberEmail(newEmail);
        }
    }

    private void validateMemberEmail(String email) {
        memberRepository.findMemberByEmail(email)
                .ifPresent(member -> {
                    throw new MemberException(ResponseCode.MEMBER_EMAIL_IS_ALREADY_EXIST);
                });
    }


}
