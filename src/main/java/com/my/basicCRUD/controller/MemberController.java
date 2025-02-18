package com.my.basicCRUD.controller;

import com.my.basicCRUD.dto.MemberDto;
import com.my.basicCRUD.entity.Member;
import com.my.basicCRUD.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("insertForm")
    public String insertFormView() {
        return "insertMember";
    }

    @PostMapping("insert")
    public String insert(MemberDto dto,
                         RedirectAttributes redirectAttributes) {
        //받은 DTO를 서비세이 넘겨주고, 저장 요청한다.
        memberService.saveMember(dto);
        redirectAttributes.addFlashAttribute("msg",
                "신규데이터가 입력되었습니다.");
        return "redirect:/member/view";
    }

    @GetMapping("view")
    public String showMember(Model model) {
        List<MemberDto> memberList = memberService.findAllMembers();
        model.addAttribute("list", memberList);
        System.out.println(memberList);
        return "showMember";
    }

    @GetMapping("delete/{id}")
    public String deleteMember(@PathVariable("id") Long id,
                               RedirectAttributes redirectAttributes) {
        memberService.deleteById(id);
        redirectAttributes.addFlashAttribute("msg",
                "선택한 자료가 삭제되었습니다.");
        return "redirect:/member/view";
    }

    @GetMapping("update/{id}")
    public String updateFormView(@PathVariable("id") Long id,
                                 Model model) {
        MemberDto dto = memberService.findById(id);
        log.info("===dto : " + dto);
        model.addAttribute("member", dto);
        return "updateMember";
    }

    @PostMapping("update")
    public String update(MemberDto dto,
                         RedirectAttributes redirectAttributes) {
        //수정 요청
        log.info("####DTO:" + dto);
        memberService.updateMember(dto);
        //메세지 전송
        redirectAttributes.addFlashAttribute("msg",
                "정상적으로 수정되었습니다.");
        return "redirect:/member/view";
    }
}
