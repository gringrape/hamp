package kr.gringrape.hamp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class JwtUtilTests {

    private final String SECRET = "IknowwhatyoudidinlastsummerandwinterYouKnowThat";

    private JwtUtil jwtUtil;

    @Before
    public void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken() {
        String token = jwtUtil.createToken(1004L, "John");

        assertThat(token).containsSequence(".");
    }

    @Test
    public void getClaims() {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsInVzZXJOYW1lIjoiSm9obiJ9.X_V7bklVELDz50tbp5w5Qj01igVrwHn9ENiogAB_rOw";

        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("userName"))
                .isEqualTo("John");

        assertThat(claims.get("userId"))
                .isEqualTo(1004);
    }

}