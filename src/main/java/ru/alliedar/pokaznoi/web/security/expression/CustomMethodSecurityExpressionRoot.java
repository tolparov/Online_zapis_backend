//package ru.alliedar.pokaznoi.web.security.expression;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.access.expression.SecurityExpressionRoot;
//import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import ru.alliedar.pokaznoi.domain.user.Role;
//import ru.alliedar.pokaznoi.service.UserService;
//import ru.alliedar.pokaznoi.web.security.JwtEntity;
//
//@Setter
//@Getter
//public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
//
//    private Object filterObject;
//    private Object returnObject;
//    private Object target;
//    private HttpServletRequest request;
//
//    private UserService userService;
//
//    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
//        super(authentication);
//    }
//
//    public boolean canAccessUser(Long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        JwtEntity user = (JwtEntity) authentication.getPrincipal();
//        Long userId = user.getId();
//        return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
//    }
//
//    private boolean hasAnyRole (Authentication authentication, Role... roles) {
//        for (Role role : roles) {
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority((role.name()));
//            if (authentication.getAuthorities().contains(authority)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean canAccessTask(Long taskId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        JwtEntity user = (JwtEntity) authentication.getPrincipal();
//        Long id = user.getId();
//
//        return userService.isTaskOwner(id, taskId);
//    }
//
//    @Override
//    public Object getThis() {
//        return target;
//    }
//
//}
