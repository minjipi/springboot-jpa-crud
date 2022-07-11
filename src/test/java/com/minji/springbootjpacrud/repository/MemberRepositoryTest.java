package com.minji.springbootjpacrud.repository;

import com.minji.springbootjpacrud.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest

public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 등록() {
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder().name("no. " + i).
                    build();
            memberRepository.save(member);
        });
    }

    @Test
    public void 조회_findById() {
        Long id = 30L;
        Optional<Member> result = memberRepository.findById(id);
        System.out.println("===============");

        if (result.isPresent()) {
            Member member = result.get();
            System.out.println(member);
        }
    }

    @Test
    public void 수정() {

        Member member = Member.builder().
                id(30L).name("new member: ").
                build();
        System.out.println(memberRepository.save(member));
    }

    @Test
    public void 삭제() {
        Long id = 30L;
        memberRepository.deleteById(id);
    }

    @Test
    public void 페이징() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Member> result = memberRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("========");
        System.out.println("총 페이지 수: " + result.getTotalPages());
        System.out.println("전체 맴버 수: " + result.getTotalElements());
        System.out.println("시작 페이지 여부: " + result.isFirst());
        System.out.println("현재 페이지 번호: " + result.getNumber());
        System.out.println("페이지당 데이터 수: " + result.getSize());
        System.out.println("다음 페이지: " + result.hasNext());

        System.out.println("========");
        System.out.println("현재 페이지 내용: " + result.getNumber());
        for (Member member : result.getContent()) {
            System.out.println(member);
        }
        System.out.println("========");
    }

    @Test
    public void 페이징_정렬() {
        Sort sort1 = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Member> result = memberRepository.findAll(pageable);

        result.get().forEach(member -> {
            System.out.println(member);
        });
    }

    @Test
    public void 쿼리메서드() {
        List<Member> list = memberRepository.findByIdBetweenOrderByIdDesc(10L, 20L);
        for (Member member : list) {
            System.out.println(member);
        }
    }

    @Test
    public void 쿼리메서드_페이징() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Member> result = memberRepository.findByIdBetween(10L, 20L, pageable);
        result.get().forEach(member -> System.out.println(member));
    }

}
