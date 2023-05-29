package com.webpower.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.webpower.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SplitScreenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SplitScreen.class);
        SplitScreen splitScreen1 = new SplitScreen();
        splitScreen1.setId(1L);
        SplitScreen splitScreen2 = new SplitScreen();
        splitScreen2.setId(splitScreen1.getId());
        assertThat(splitScreen1).isEqualTo(splitScreen2);
        splitScreen2.setId(2L);
        assertThat(splitScreen1).isNotEqualTo(splitScreen2);
        splitScreen1.setId(null);
        assertThat(splitScreen1).isNotEqualTo(splitScreen2);
    }
}
