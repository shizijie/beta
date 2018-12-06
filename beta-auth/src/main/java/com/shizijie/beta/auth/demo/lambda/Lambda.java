package com.shizijie.beta.auth.demo.lambda;

import com.shizijie.beta.auth.dto.UserDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lambda {
    public static Function<String, UserDTO> setUser= fun->{UserDTO dto=new UserDTO();dto.setUserName(fun);return dto;};

    public static void main(String[] args) {
        String[] strArr={"1","2","3","4"};
        Map<String,UserDTO> map= Stream.of(strArr).collect(Collectors.toMap(a->a,a->setUser.apply(a)));
        List<String> list= Arrays.asList(strArr);
    }
}
