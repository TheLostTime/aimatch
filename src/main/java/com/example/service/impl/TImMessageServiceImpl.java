package com.example.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.TImMessageMapper;
import com.example.entity.TImMessage;
import com.example.service.TImMessageService;
@Service
public class TImMessageServiceImpl extends ServiceImpl<TImMessageMapper, TImMessage> implements TImMessageService{

}
