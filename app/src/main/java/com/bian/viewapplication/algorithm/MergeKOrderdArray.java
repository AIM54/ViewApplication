package com.bian.viewapplication.algorithm;

import java.util.PriorityQueue;

public class MergeKOrderdArray {

    private PriorityQueue<String> priorityQueue;

    public void testQueue() {
    }

    public class RecordClass {
        public int mData[];
        public int currentIndex;

        public int peek() {
            return mData[currentIndex];
        }

        public int pop() {
            if (currentIndex >= mData.length) {
                return Integer.MIN_VALUE;
            }
            return mData[currentIndex++];
        }


    }

    public class RecordStack {
        private RecordClass[] mData;
        private int mCurrentHeapIndex;

        public RecordStack(int size) {
            mData = new RecordClass[size];
            mCurrentHeapIndex = 0;
            buildHeap();
        }

        private void buildHeap() {
            for (int insetIndex = 0; insetIndex < mData.length; insetIndex++) {
                if (insetIndex == 0) {
                    continue;
                }
                insertValue(mData[insetIndex]);
            }
        }

        private void insertValue(RecordClass record) {
            mCurrentHeapIndex++;
            int targetIndex = mCurrentHeapIndex;
            while (targetIndex > 0) {
                int parentIndex = (targetIndex - 1) >>> 1;
                RecordClass parent = mData[parentIndex];
                if (parent.peek() <= mData[parentIndex].peek()) {
                    break;
                } else {
                    mData[targetIndex] = mData[parentIndex];
                    targetIndex = parentIndex;
                }
            }
            mData[targetIndex] = record;
        }

        public RecordClass popValue() {
            return mData[0];

        }
    }


}
