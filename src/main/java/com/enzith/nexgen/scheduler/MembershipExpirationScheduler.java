package com.enzith.nexgen.scheduler;

import com.enzith.nexgen.service.MemberMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipExpirationScheduler {

    private final MemberMembershipService memberMembershipService;

    @Scheduled(cron = "0 0 */6 * * ?")
    public void processMemberExpirations() {
        memberMembershipService.expireMembership();
    }

}
