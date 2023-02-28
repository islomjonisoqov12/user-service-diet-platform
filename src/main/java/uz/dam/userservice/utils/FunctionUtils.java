package uz.dam.userservice.utils;

import uz.dam.userservice.repositories.RoleRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.*;

import static uz.dam.userservice.utils.ConsoleColors.*;


public interface FunctionUtils {
     static UnaryOperator<String> toSql = s -> {
        if(s==null){
            return null;
        }
        return String.format("'%s'", s);
    };
     static Function<Object, String> toString = o -> {
        if(o==null){
            return null;
        }
        return String.format("'%s'", o.toString());
    };

     static UnaryOperator<String> toSearchSql = s -> {
         if(s==null) return "%";
         return String.format("%%s%",s.replace("'", "''"));
     };

     static BiConsumer<String, String> printColored = (s, c) -> {
         System.out.println(c+s+RESET);
     };
     static BiConsumer<String, Object> print = (s, o)-> {
         String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
         System.out.println(BLUE_BOLD + format + "  >>>>>>>>>>>>>>>>> " +RESET + PURPLE_UNDERLINED + s +RESET +" "+ CYAN_BOLD +o.toString()+ RESET);
    };
     static Predicate<String> hasRolePrefix =role -> {
         if (role==null || role.isEmpty()) return true;
         return role.toUpperCase().startsWith("ROLE_");
     };

     static String generateHasRole(String... a){
         StringBuilder res = new StringBuilder("hasAuthority(");
         for (int i = 0; i < a.length-1; i++) {
             res.append("'").append(a[i]).append("',");
         }
         res.append("'").append(a[a.length-1]).append("')");
         return res.toString();
     }


}
