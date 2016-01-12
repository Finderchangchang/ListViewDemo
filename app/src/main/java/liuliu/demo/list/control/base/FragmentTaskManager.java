package liuliu.demo.list.control.base;

import android.app.Activity;
import android.app.Fragment;

import java.util.HashMap;
import java.util.Set;

/**
 * 一个Fragment管理器管理活动的Fragment
 */
public class FragmentTaskManager {
    private static FragmentTaskManager fragmentTaskManager = null;
    private HashMap<String, Fragment> activityMap = null;

    private FragmentTaskManager() {
        activityMap = new HashMap<String, Fragment>();
    }

    /**
     * 返回activity管理器的唯一实例对象。
     *
     * @return fragmentTaskManager
     */
    public static synchronized FragmentTaskManager getInstance() {
        if (fragmentTaskManager == null) {
            fragmentTaskManager = new FragmentTaskManager();
        }
        return fragmentTaskManager;
    }

    /**
     * 将一个fragment添加到管理器。
     *
     * @param fragment
     */
    public Fragment putFragment(String name, Fragment fragment) {
        return activityMap.put(name, fragment);
    }

    /**
     * 得到保存在管理器中的Fragment对象。
     *
     * @param name
     * @return Fragment
     */
    public Fragment getFragment(String name) {
        return activityMap.get(name);
    }

    /**
     * 返回管理器的Fragment是否为空。
     *
     * @return 当且当管理器中的Fragment对象为空时返回true，否则返回false。
     */
    public boolean isEmpty() {
        return activityMap.isEmpty();
    }

    /**
     * 返回管理器中Fragment对象的个数。
     *
     * @return 管理器中Fragment对象的个数。
     */
    public int size() {
        return activityMap.size();
    }

    /**
     * 返回管理器中是否包含指定的名字。
     *
     * @param name 要查找的名字。
     * @return 当且仅当包含指定的名字时返回true, 否则返回false。
     */
    public boolean containsName(String name) {
        return activityMap.containsKey(name);
    }

    /**
     * 返回管理器中是否包含指定的Fragment。
     *
     * @param fragment 要查找的Fragment。
     * @return 当且仅当包含指定的Fragment对象时返回true, 否则返回false。
     */
    public boolean containsFragment(Fragment fragment) {
        return activityMap.containsValue(fragment);
    }

    /**
     * 移除Fragment对象,如果它未结束则结束它。
     *
     * @param name Fragment对象的名字。
     */
    public void removeFragment(String name) {
        activityMap.remove(name);
    }
}
