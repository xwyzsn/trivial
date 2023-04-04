package com.example.demo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xwyzsn
 * @since 2023-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaintenanceRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer stationId;

    private String stationName;

    private String address;

    private Integer maintenanceStaffId;

    private String maintenanceStaffName;

    private String description;

    private Date reportTime;

    private Date finishTime;

    private BigDecimal cost;

    private String status;


}
