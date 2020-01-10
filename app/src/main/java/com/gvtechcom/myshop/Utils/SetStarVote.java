package com.gvtechcom.myshop.Utils;

import com.gvtechcom.myshop.R;

public class SetStarVote {

    private String setStartNumber(Double vote) {
        String star = "";
        if (vote > 4.5) {
            star = "5," + R.string.oneStar + R.string.oneStar + R.string.oneStar + R.string.oneStar + R.string.oneStar;
        } else {
            if (vote > 4) {
                star = "4.5," + R.string.oneStar + R.string.oneStar + R.string.oneStar + R.string.oneStar + R.string.haftStar;
            } else {
                if (vote >= 3.5) {
                    star = "4," + R.string.oneStar + R.string.oneStar + R.string.oneStar + R.string.oneStar + R.string.noStart;
                } else {
                    if (vote > 3) {
                        star = "3.5," + R.string.oneStar + R.string.oneStar + R.string.oneStar + R.string.haftStar + R.string.noStart;
                    } else {
                        if (vote > 2.5) {
                            star = "3," + R.string.oneStar + R.string.oneStar + R.string.oneStar + R.string.noStart + R.string.noStart;
                        } else {
                            if (vote > 2) {
                                star = "2.5," + R.string.oneStar + R.string.oneStar + R.string.haftStar + R.string.noStart + R.string.noStart;
                            } else {
                                if (vote > 1.5) {
                                    star = "2," + R.string.oneStar + R.string.oneStar + R.string.noStart + R.string.noStart + R.string.noStart;
                                } else {
                                    if (vote > 1) {
                                        star = "1.5," + R.string.oneStar + R.string.haftStar + R.string.noStart + R.string.noStart + R.string.noStart;
                                    } else {
                                        star = "1," + R.string.oneStar + R.string.noStart + R.string.noStart + R.string.noStart + R.string.noStart;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return star;
    }

}
