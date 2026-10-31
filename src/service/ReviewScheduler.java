package service;

import java.time.LocalDate;
import java.util.ArrayList;
import model.*;

public class ReviewScheduler {
    private final int[] cycles = {1, 3, 7, 14, 30, 60, 90}; //망각곡선의 주기

    public void completeReview(WrongAnswer wa) {
        int stage = wa.getStage();
        if (stage >= cycles.length - 1) {   // 마지막 단계까지 마쳤으면 보관함으로
            wa.setArchived(true);
            return;
        }
        stage = stage + 1;
        wa.setStage(stage);
        wa.setNextReviewDate(LocalDate.now().plusDays(cycles[stage]));
    }

    // 보관함에서 꺼내 처음부터 다시 복습
    public void restart(WrongAnswer wa) {
        wa.setArchived(false);
        wa.setStage(0);
        wa.setNextReviewDate(LocalDate.now().plusDays(cycles[0]));
    }

    public ArrayList<WrongAnswer> getTodayList(ArrayList<WrongAnswer> all) {
        ArrayList<WrongAnswer> today = new ArrayList<>();
        LocalDate now = LocalDate.now();

        for (int i = 0; i < all.size(); i++) {
            WrongAnswer wa = all.get(i); // i번째 오답을 꺼내서 wa변수에 담음
            if (wa.isArchived()) continue;   // 보관된 오답은 복습 대상에서 제외
            if(wa.getNextReviewDate().isBefore(now) || wa.getNextReviewDate().isEqual(now)) {
                today.add(wa);
            }
        }
        return today;
    }

}
