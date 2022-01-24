<template>
    <b-container class="bv-example-row mt-3">
        <h2>여기는 Notice Create</h2>
        <b-form-group
            id="title-group"
            label="제목:"
            label-for="title"
            description="제목을 입력하세요."
        >

            <b-form-input
                id="title"
                ref="title"
                type="text"
                required
                placeholder="제목 입력..."
            />
        </b-form-group>

        <b-form-group id="question-group" label="내용:" label-for="question">
            <b-form-textarea
                id="question"
                ref="question"
                placeholder="내용 입력..."
                rows="10"
                max-rows="15"
            >
            </b-form-textarea>
        </b-form-group>


    </b-container>
</template>