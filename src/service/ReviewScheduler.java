package service;

import java.time.LocalDate;
import java.util.ArrayList;
import model.*;

public class ReviewScheduler {
    private final int[] cycles = {1, 3, 7, 14, 30}; //망각곡선의 주기

    public void completeReview(WrongAnswer wa) {
        int stage = wa.getStage();
        if (stage < cycles.length - 1) {
            stage = stage + 1;
        }
        wa.setStage(stage);
        wa.setNextReviewDate(LocalDate.now().plusDays(cycles[stage]));
    }

    public ArrayList<WrongAnswer> getTodayList(ArrayList<WrongAnswer> all) {
        ArrayList<WrongAnswer> today = new ArrayList<>();
        LocalDate now = LocalDate.now();

        for (int i = 0; i < all.size(); i++) {
            WrongAnswer wa = all.get(i); // i번째 오답을 꺼내서 wa변수에 담음
            if(wa.getNextReviewDate().isBefore(now) || wa.getNextReviewDate().isEqual(now)) {
                today.add(wa);
            }
        }
        return today;
    }

}
