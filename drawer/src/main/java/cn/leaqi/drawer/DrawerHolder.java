/*
    Copyright 2022 Leaqi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package cn.leaqi.drawer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The function of DrawerHolder is to save the state of SwipeDrawer
 * Created by Leaqi.
 * Github: https://github.com/Leaqi
 */
public class DrawerHolder implements OnDrawerSwitch {

    private Map<SwipeDrawer, Object> listView = Collections.synchronizedMap(new HashMap<SwipeDrawer, Object>());
    private Map<Object, Integer> listMap = Collections.synchronizedMap(new HashMap<Object, Integer>());

    public void bindHolder(final SwipeDrawer view, final Object obj) {
        try {
            if (!listView.containsKey(view)) {
                view.setOnDrawerSwitch(this);
            }
            listView.put(view, obj);
            if (listMap.containsKey(obj)) {
                final Integer getData = listMap.get(obj);
                if (getData != null) {
                    if (view.getShow() && view.getDirection() != getData) {
                        view.closeDrawer(false, false);
                    }
                    view.openDrawer(getData, false, false);
                }
            } else {
                if (view.getShow()) {
                    view.closeDrawer(false, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delHolder(SwipeDrawer view) {
        try {
            if (listView.containsKey(view)) {
                Object obj = listView.get(view);
                if (obj == null) return;
                listMap.remove(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearHolder() {
        try {
            for (Map.Entry<SwipeDrawer, Object> val : listView.entrySet()) {
                val.getKey().forceClose(false);
            }
            listMap.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearView() {
        try {
            listView.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            clearHolder();
            listView.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(SwipeDrawer view) {
        try {
            if (listView.containsKey(view)) {
                Object obj = listView.get(view);
                if (obj == null) return;
                if (!listMap.containsKey(obj)) {
                    listMap.put(obj, view.getDirection());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(SwipeDrawer view) {
        delHolder(view);
    }

}
