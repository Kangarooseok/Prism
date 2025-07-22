<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: 'Controller' }" />
        <VerticalNavLink
            :item="{
                title: 'Cctv',
                to: '/cctvs',
            }"
        />
        <VerticalNavLink
            :item="{
                title: 'User',
                to: '/users',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Notification' }" />
        <VerticalNavLink
            :item="{
                title: '알림',
                to: '/notifications',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Alertsubscription' }" />
        <VerticalNavLink
            :item="{
                title: '알람구독',
                to: '/alertSubscriptions',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Healthchecklog' }" />
        <VerticalNavLink
            :item="{
                title: '상태 점검 로그',
                to: '/healthCheckLogs',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Issue' }" />
        <VerticalNavLink
            :item="{
                title: 'Issue',
                to: '/issues',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Cctv' }" />
        <VerticalNavLink
            :item="{
                title: 'TvResolution',
                to: '/tvResolutions',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Network' }" />
        <VerticalNavLink
            :item="{
                title: 'NetworkAction',
                to: '/networkActions',
            }"
        />
    </ul>
</template>
