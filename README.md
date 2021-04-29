# multi_core_assignment
2021-1학기

## Problem 1

1) Static, Dynamic Load balance를 구현하여 소수 갯수 세는 모듈을 만든 후 보고서 작성 
    Static은 그냥 할당, Dynamic은 Task Queue를 스레드끼리 공유하도록 하여 
    
결과:
   Static의 Load balance를 thread 갯수만큼 순회하며 1부터 200000까지 작업을 할당 했는데 짝수번째만 들어가게 되어 매우 안 좋은 Load Balance를 보임
    
   Dynamic의 경우는 공유 작업 큐를 만들어 각 스레드가 race-condition에서 FIFO 정책에 따라 작업을 가져와 수행하도록 하여 매우 좋은 Load Balacne를 보여줌
    
   두 경우 모두, 8스레드까지는 어느 정도 기대치 만큼 성능 향상이 있었으나 그 이후로는 개선폭 (Marginal Performance Increase)가 매우 안 좋아짐.

   ![image](https://user-images.githubusercontent.com/45940359/116524512-6f7e8480-a912-11eb-9bdc-125324b65681.png)
    
   ![image](https://user-images.githubusercontent.com/45940359/116524522-71e0de80-a912-11eb-8efc-c32dbe0698c7.png)


## Problem 2

2) Matrix Multiplication Static Load Balance를 통해 구현

    별것은 없고 그냥 row, col vector를 load balance의 단위로 하여 행렬을 계산하도록 함.
    
    후에 심심해서 기능을 추가할 수도 있다고 생각하여 따로 Matrix란 클래스를 만들어서 진행함.
    
    ![image](https://user-images.githubusercontent.com/45940359/116524585-845b1800-a912-11eb-9afb-e8486d3647fa.png)
    
    ![image](https://user-images.githubusercontent.com/45940359/116524599-87ee9f00-a912-11eb-821d-fb61042d77b7.png)

    "그림에서 14번째의 스레드의 성능이 갑자기 안 좋아지는 것은 밝혀내지 못하였음..."


