package ai0825;

import java.util.Random;

public class LABRandomWiseSaying {
    public static void main(String[] args) {
        String[] wiseSay = {
                        "성공이란 열정을 잃지 않고 실패를 거듭하는 것이다.” — 윈스턴 처칠",
                        "우리의 가장 큰 영광은 한 번도 넘어지지 않는 데 있는 것이 아니라, 넘어질 때마다 일어나는 데 있다.” — 공자",
                        "할 수 있다고 믿든, 할 수 없다고 믿든, 네 말이 맞다.” — 헨리 포드",
                        "행동이 모든 성공의 기초다.” — 파블로 피카소",
                        "미래는 오늘 무엇을 하느냐에 달려 있다.” — 마하트마 간디",
                        "삶은 우리가 만드는 것이다. 늘 그래왔고, 늘 그럴 것이다.” — 엘리너 루스벨트",
                        "성공은 최종적인 것이 아니며, 실패는 치명적인 것이 아니다. 계속 나아갈 용기가 중요하다.” — 윈스턴 처칠",
                        "시작하기 위해 위대할 필요는 없지만, 위대해지기 위해서는 시작해야 한다.” — 지그 지글러",
                        "어려움 속에서도 기회가 있다.” — 알베르트 아인슈타인",
                        "나는 실패하지 않았다. 단지 작동하지 않는 방법을 많이 발견했을 뿐이다.” — 토머스 에디슨"
        };

        Random random = new Random();
        int randomIndex = random.nextInt(wiseSay.length);
        System.out.println("오늘의 명언: " + wiseSay[randomIndex]);
    }
}
