package com.dickow.chortlin.core.configuration.lookup

class TreeNode<TKey, TValue>(
        private val key: TKey,
        private val value: TValue,
        private val children: Collection<TreeNode<TKey, TValue>>) {

    fun get(key: TKey): TValue? {
        if (this.key == key) {
            return value
        }

        return children.firstOrNull { it.get(key) != null }?.get(key)
    }
}