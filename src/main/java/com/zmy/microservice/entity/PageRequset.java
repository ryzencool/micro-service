package com.zmy.microservice.entity;

/*
 * Copyright (C) 2018 The gingkoo Authors
 * This file is part of The gingkoo library.
 *
 * The gingkoo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The gingkoo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with The gingkoo.  If not, see <http://www.gnu.org/licenses/>.
 */

import lombok.Data;

/**
 * @description: 需要分页的对象继承该类，使用使用PageHelper来完成
 * @author: zmy
 * @create: 2018/6/22
 */
@Data
public class PageRequset {

    /**
     * 页数，传1和传0都是查询第一页
     */
    private int pageNum = 0;

    /**
     * 每页条数
     */
    private int pageSize = 10;

}
