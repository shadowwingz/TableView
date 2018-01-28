/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.evrencoskun.tableview.handler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.evrencoskun.tableview.ITableView;
import com.evrencoskun.tableview.layoutmanager.CellLayoutManager;
import com.evrencoskun.tableview.layoutmanager.ColumnLayoutManager;

/**
 * Created by evrencoskun on 13.01.2018.
 */

public class ScrollHandler {

    private ITableView mTableView;
    private CellLayoutManager mCellLayoutManager;
    private LinearLayoutManager mRowHeaderLayoutManager;

    public ScrollHandler(ITableView tableView) {
        this.mTableView = tableView;
        this.mCellLayoutManager = tableView.getCellLayoutManager();
        this.mRowHeaderLayoutManager = tableView.getRowHeaderLayoutManager();
    }

    public void scrollToColumnPosition(int columnPosition) {
        // TableView is not on screen yet.
        if (!((View) mTableView).isShown()) {
            // Change default value of the listener
            mTableView.getHorizontalRecyclerViewListener().setScrollPosition(columnPosition);
        }

        // Column Header should be scrolled firstly because of fitting column width process.
        scrollColumnHeader(columnPosition);
        scrollCellHorizontally(columnPosition);
    }

    public void scrollToRowPosition(int rowPosition) {
        mRowHeaderLayoutManager.scrollToPosition(rowPosition);
        mCellLayoutManager.scrollToPosition(rowPosition);
    }


    private void scrollCellHorizontally(int columnPosition) {
        CellLayoutManager cellLayoutManager = mTableView.getCellLayoutManager();

        for (int i = cellLayoutManager.findFirstVisibleItemPosition(); i < cellLayoutManager
                .findLastVisibleItemPosition() + 1; i++) {

            RecyclerView cellRowRecyclerView = (RecyclerView) cellLayoutManager
                    .findViewByPosition(i);

            if (cellRowRecyclerView != null) {
                ColumnLayoutManager columnLayoutManager = (ColumnLayoutManager)
                        cellRowRecyclerView.getLayoutManager();

                columnLayoutManager.scrollToPosition(columnPosition);
            }

        }
    }

    private void scrollColumnHeader(int columnPosition) {
        mTableView.getColumnHeaderLayoutManager().scrollToPosition(columnPosition);
    }
}