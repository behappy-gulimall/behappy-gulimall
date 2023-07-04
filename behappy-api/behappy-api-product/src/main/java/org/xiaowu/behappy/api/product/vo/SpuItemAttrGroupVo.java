package org.xiaowu.behappy.api.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xiaowu
 */
@Data
public class SpuItemAttrGroupVo {

    private String groupName;

    private List<Attr> attrs;

}
