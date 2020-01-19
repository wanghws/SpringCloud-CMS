<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import homeDashboard from './home'

export default {
  name: 'Dashboard',
  components: { homeDashboard },
  data() {
    return {
      currentRole: 'homeDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    this.currentRole = 'homeDashboard'
  }
}
</script>
