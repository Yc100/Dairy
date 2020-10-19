<template>
  <div class="stepComponent">
   <Steps :current="2" direction="vertical">
    <Step title="已完成" content="这里是该步骤的描述信息"></Step>
    <Step title="已完成" content="这里是该步骤的描述信息"></Step>
    <Step title="进行中" content="这里是该步骤的描述信息"></Step>
   </Steps>
  </div>
</template>

<script>
export default {
 name:'stepComponent'
}
</script>

<style>

</style>