<template>
  <div class="errPage-container">
    <errorA />
    <errorB />
    <!-- $t is vue-i18n global function to translate lang -->
    <h3>{{ $t('errorLog.tips') }}</h3>
    <code>
      {{ $t('errorLog.description') }}
    </code>
  </div>
</template>

<script>
import errorA from './errorTestA'
import errorB from './errorTestB'

export default {
  name: 'ErrorLog',
  components: { errorA, errorB }
}
</script>

<style scoped>
  .errPage-container {
    padding: 30px;
  }
</style>
