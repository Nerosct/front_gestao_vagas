package br.com.nerosct.front_gestao_vagas.modules.candidate.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.nerosct.front_gestao_vagas.modules.candidate.controller.service.ApplyJobService;
import br.com.nerosct.front_gestao_vagas.modules.candidate.controller.service.CandidateService;
import br.com.nerosct.front_gestao_vagas.modules.candidate.controller.service.FindJobService;
import br.com.nerosct.front_gestao_vagas.modules.candidate.controller.service.ProfileCandidateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private ProfileCandidateService profileCandidateService;

    @Autowired
    private FindJobService findJobService;

    @Autowired
    private ApplyJobService applyJobService;

    @GetMapping("/login")
    public String login() {
        return "candidate/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession httpSession, String username,
            String password) {
        try {
            var token = this.candidateService.loginCandidate(username, password);
            var grants = token.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, grants);

            auth.setDetails(token.getAccess_token());
            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            httpSession.setAttribute("token", token);

            return "redirect:/candidate/profile";
        } catch (HttpClientErrorException e) {
            redirectAttributes.addFlashAttribute("error_message", "Usuário ou senha inválidos");
            return "redirect:/candidate/login";
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String profile(Model model) {

        try {
            var user = this.profileCandidateService.execute(getToken());
            model.addAttribute("user", user);
            return "candidate/profile";
        } catch (HttpClientErrorException e) {
            return "redirect:/candidate/login";
        }
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String jobs(String filter, Model model) {

        try {
            var jobs = this.findJobService.execute(getToken(), filter);
            System.out.println(jobs);
            model.addAttribute("jobs", jobs);
        } catch (HttpClientErrorException e) {
            return "redirect:/candidate/login";
        }
        return "candidate/jobs";
    }

    @PostMapping("/jobs/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String applyJob(@RequestParam("jobId") UUID jobId, Model model) {
        try {
            this.applyJobService.execute(getToken(), jobId);
            System.out.println("JOB ID: " + jobId);
            return "redirect:/candidate/jobs";
        } catch (HttpClientErrorException e) {
            return "redirect:/candidate/login";
        }
    }


    @GetMapping("/create")
    public String create(){
        return "candidate/create";
    }


    private String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }

}
